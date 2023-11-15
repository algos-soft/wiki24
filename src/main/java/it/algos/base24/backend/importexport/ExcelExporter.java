package it.algos.base24.backend.importexport;

import it.algos.base24.backend.layer.*;
import it.algos.base24.backend.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.commons.beanutils.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.util.*;

import java.io.*;
import java.lang.reflect.*;
import java.math.*;
import java.time.*;
import java.util.*;

/**
 * Oggetto che esporta una lista di entity in formato Excel
 */
@Slf4j
public class ExcelExporter implements Runnable {

    public MongoService mongoService;

    private Class entityClass;

    // default bean of the entityClass needed by some reflection operations
    // we build it only once and keep it in a variable.
    private Object defaultBean;

    @Setter
    private FiltroSort filtroSort;

    private List<String> propertyNames = new ArrayList<>();

    private List<List> data;

    private List providedItems;

    @Setter
    private String title;

    private XSSFWorkbook workbook;

    @Setter
    private String sheetName = "Generale";

    private XSSFFont defaultFont;

    private XSSFFont defaultFontBold;

    private Map<String, String> columnTitles = new HashMap<>();

    private Map<String, Integer> columnWidths = new HashMap<>();

    private Map<String, HorizontalAlignment> columnAlignments = new HashMap<>();

    public ExcelExporter() {
    }


    public ExcelExporter(Class entityClass, List<String> propertyNames, MongoService mongoService) {
        this.entityClass = entityClass;
        this.propertyNames = propertyNames;
        this.mongoService = mongoService;
        buildDefaultBean();
        setColumnTitles(propertyNames);
    }

    public ExcelExporter(Class entityClass, FiltroSort filtroSort, List<String> propertyNames, MongoService mongoService) {
        this.entityClass = entityClass;
        this.filtroSort = filtroSort;
        this.propertyNames = propertyNames;
        this.mongoService = mongoService;
        buildDefaultBean();
        setColumnTitles(propertyNames);
    }

    /**
     * Creates an ExcelExporter from a list of items.
     * <p>
     * The items must be all of the same type.
     */
    public ExcelExporter(List items, List<String> propertyNames) {

        // check at least 1 item
        if (items == null || items.size() == 0) {
            items = new ArrayList();
        }

        // check all items of the same type and retrieve the class
        Class firstClazz;
        if (items.size() > 0) {
            firstClazz = items.get(0).getClass();
            for (Object o : items) {
                Class clazz = o.getClass();
                if (!clazz.equals(firstClazz)) {
                    throw new RuntimeException("tried to create ExcelExporter with list of objects of different classes");
                }
            }
        }
        else {
            firstClazz = Object.class;
        }

        this.entityClass = firstClazz;
        this.providedItems = items;
        this.propertyNames = propertyNames;
        buildDefaultBean();
        setColumnTitles(propertyNames);

    }

    @Override
    public void run() {

        data = createDataMatrix(entityClass, filtroSort, propertyNames);

        workbook = new XSSFWorkbook();
        defaultFont = createFont(workbook);
        defaultFontBold = createFont(workbook);
        defaultFontBold.setBold(true);

        XSSFSheet sheet = workbook.createSheet(sheetName);
        setDataFormats(sheet);

        int rowIdx = 0;

        if (!StringUtils.isEmpty(title)) {
            createSheetTitle(sheet);
            rowIdx++;
        }

        if (columnTitles.size() > 0) {
            createColumnHeaders(sheet, rowIdx);
            rowIdx++;
        }

        for (List dataRow : data) {
            Row sheetRow = sheet.createRow(rowIdx);
            processItem(dataRow, sheetRow);
            rowIdx++;
        }

        // called after the data is present to allow for autosize
        setColumnWidths(sheet);

    }

    private void buildDefaultBean() {
        try {
            Constructor constructor = entityClass.getDeclaredConstructor();
            defaultBean = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            log.error("can't instantiate bean of type " + entityClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve the class for a given property by reflection
     *
     * @param propertyName the propertyName
     *
     * @return the class
     */
    private Class getClassForProperty(String propertyName) {
        Class clazz;
        try {
            clazz = PropertyUtils.getPropertyType(defaultBean, propertyName);
        } catch (Exception e) {
            log.error("can't find property '" + propertyName + "' in bean of type " + entityClass.getName(), e);
            throw new RuntimeException(e);
        }
        return clazz;
    }


    /**
     * Genera una matrice di dati righe e colonne.
     *
     * @param entityClass   la classe delle entity
     * @param propertyNames i nomi delle property da includere
     * @param filtroSort    filtri e ordine per selezionare le righe
     *
     * @return una matrice nella forma di lista di liste di oggetti
     */
    private List<List> createDataMatrix(Class entityClass, FiltroSort filtroSort, List<String> propertyNames) {
        List data = new ArrayList();
        if (providedItems == null) {
            Query query = filtroSort != null ? filtroSort.getQuery() : new Query();
            List items = mongoService.find(query, entityClass);

            for (Object item : items) {
                data.add(getValues(item, propertyNames));
            }
        }
        else {
            for (Object item : providedItems) {
                data.add(getValues(item, propertyNames));
            }
        }
        return data;
    }

    private List getValues(Object item, List<String> propertyNames) {
        List values = new ArrayList();
        for (String name : propertyNames) {
            Object value = null;
            try {
                value = PropertyUtils.getProperty(item, name);
            } catch (Exception e) {
                log.error("property '" + name + "' not found in bean of type " + item.getClass().getName(), e);
            }
            values.add(value);
        }
        return values;
    }


    private XSSFFont createFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        return font;
    }

    private void createColumnHeaders(XSSFSheet sheet, int rowIndex) {
        Row sheetRow = sheet.createRow(rowIndex);
        int numcol = 0;
        for (String propertyName : propertyNames) {
            String columnTitle = columnTitles.get(propertyName);
            if (!StringUtils.isEmpty(columnTitle)) {
                Cell cell = sheetRow.createCell(numcol);
                cell.setCellValue(columnTitle);
                DataFormat fmt = workbook.createDataFormat();
                CellStyle style = workbook.createCellStyle();
                style.setDataFormat(fmt.getFormat("@"));
                style.setFont(defaultFontBold);
                cell.setCellStyle(style);
            }
            numcol++;
        }
    }


    private void createSheetTitle(XSSFSheet sheet) {
        Row sheetRow = sheet.createRow(0);
        sheetRow.setHeightInPoints((short) 24);

        Cell cell = sheetRow.createCell(0);
        cell.setCellValue(title);

        DataFormat fmt = workbook.createDataFormat();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(fmt.getFormat("@"));
        style.setAlignment(HorizontalAlignment.LEFT);

        Font font = createFont(workbook);
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
    }


    /**
     * Assign the default format to each column based on the property type
     */
    private void setDataFormats(XSSFSheet sheet) {

        int colIndex = 0;
        for (String name : propertyNames) {
            Class clazz = getClassForProperty(name);
            String format = getFormatForType(clazz);
            DataFormat fmt = workbook.createDataFormat();
            CellStyle style = workbook.createCellStyle();
            style.setDataFormat(fmt.getFormat(format));
            style.setFont(defaultFont);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            // set the horizontal alignment, default is LEFT
            HorizontalAlignment alignment = columnAlignments.get(name);
            if (alignment == null) {
                alignment = HorizontalAlignment.LEFT;
            }
            style.setAlignment(alignment);

            sheet.setDefaultColumnStyle(colIndex, style);

            colIndex++;
        }
    }


    private void setColumnWidths(XSSFSheet sheet) {
        int columnIdx = 0;
        boolean skipSetWidth;
        for (String propertyName : propertyNames) {
            skipSetWidth = false;

            // if not specified, it's default width
            Integer width = columnWidths.get(propertyName);
            int w;
            if (width == null) {
                w = 0;
            }
            else {
                w = width;
            }

            // Patch for dates with width equal to default or to autosize:
            // since default is too narrow, and since autosize doesn't work correctly for dates,
            // we force the width to 10.
            Class clazz = getClassForProperty(propertyName);
            if (clazz.equals(Date.class) || clazz.equals(LocalDate.class)) {
                if (w == 0 || w == -1) {
                    w = 10;
                }
            }

            // if default or out of range, we do nothing
            if (w == 0 || w > 255) {
                skipSetWidth = true;
            }

            // if autosize, just call the autosize function and do nothing
            if (w == -1) {
                sheet.autoSizeColumn(columnIdx);
                skipSetWidth = true;
            }

            if (!skipSetWidth) {
                sheet.setColumnWidth(columnIdx, w * 256);
            }

            columnIdx++;
        }

    }

    public InputStream getInputStream() {

        run();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }

    private int getColumnCount() {
        int maxSize = 0;
        for (List row : data) {
            if (row.size() > maxSize) {
                maxSize = row.size();
            }
        }
        return maxSize;
    }

    public void setColumnTitle(String propertyName, String columnTitle) {
        columnTitles.put(propertyName, columnTitle);
    }

    /**
     * Set the column titles in the same order as the properties.
     * Overrides any previous setting of column titles.
     */
    public void setColumnTitles(List<String> titles) {
        columnTitles.clear();
        int i = 0;
        for (String title : titles) {
            String key = propertyNames.get(i);
            columnTitles.put(key, title);
            i++;
        }
    }


    /**
     * Set the width of a column in characters.
     *
     * @param propertyName the name of the corresponding property
     * @param width        the width in characters
     *                     0 = default width
     *                     -1 = autosize the column based on contents
     *                     1-255 = the width (max 255)
     */
    public void setColumnWidth(String propertyName, int width) {
        columnWidths.put(propertyName, width);
    }

    /**
     * Set width for all the columns
     * Overwrites any previous setting
     *
     * @param width the width in characters
     *              0 = default width
     *              -1 = autosize the column based on contents
     *              1-255 = the width (max 255)
     */
    public void setColumnWidth(int width) {
        columnWidths.clear();
        for (String name : propertyNames) {
            columnWidths.put(name, width);
        }
    }

    /**
     * Set the horizontal alignment for a column
     *
     * @param propertyName the name of the corresponding property
     * @param alignment    the HorizontalAlignment value
     */
    public void setColumnAlignment(String propertyName, HorizontalAlignment alignment) {
        columnAlignments.put(propertyName, alignment);
    }

    /**
     * Set the horizontal alignment for all the columns
     * Overwrites any previous setting
     *
     * @param alignment the HorizontalAlignment value
     */
    public void setColumnAlignment(HorizontalAlignment alignment) {
        columnAlignments.clear();
        for (String name : propertyNames) {
            columnAlignments.put(name, alignment);
        }
    }

    private void processItem(List dataRow, Row sheetRow) {

        for (int column = 0; column < getColumnCount(); column++) {

            Cell cell = sheetRow.createCell(column);

            Object value = dataRow.get(column);

            if (value == null) {
                cell.setCellValue("");
                continue;
            }

            Class clazz = value.getClass();
            boolean found = false;

            if (clazz.equals(Number.class)) {
                Number number = (Number) value;
                cell.setCellValue(number.doubleValue());
                found = true;
            }

            if (clazz.equals(String.class)) {
                cell.setCellValue((String) value);
                found = true;
            }

            if (clazz.equals(Boolean.class)) {
                cell.setCellValue((Boolean) value);
                found = true;
            }

            if (clazz.equals(Date.class)) {
                cell.setCellValue((Date) value);
                found = true;
            }

            if (clazz.equals(LocalDate.class)) {
                LocalDate localDate = (LocalDate) value;
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                cell.setCellValue(date);
                found = true;
            }

            if (clazz.equals(LocalDateTime.class)) {
                LocalDateTime localDateTime = (LocalDateTime) value;
                ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
                Date date = Date.from(zdt.toInstant());
                cell.setCellValue(date);
                found = true;
            }

            if (!found) {
                cell.setCellValue(value.toString());
            }

        }
    }


    /**
     * Determine the most adequate cell format to represent a given type
     */
    private String getFormatForType(Class clazz) {

        if (clazz.equals(String.class)) {
            return "@";
        }

        if (clazz.equals(Byte.class) || clazz.equals(Short.class) || clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(BigInteger.class)) {
            return "0";
        }

        if (clazz.equals(Float.class) || clazz.equals(Double.class) || clazz.equals(BigDecimal.class)) {
            return "0.00";
        }

        if (clazz.equals(Date.class) || clazz.equals(LocalDate.class)) {
            return "dd-mm-yyyy";
        }

        if (clazz.equals(LocalDateTime.class)) {
            return "dd-mm-yyyy h:mm:ss";
        }

        return "@";
    }


}
