package it.algos.base24.backend.enumeration;


import it.algos.base24.backend.interfaces.*;

import java.util.*;

/**
 * Project base2023
 * Created by Algos
 * User: gac
 * Date: Thu, 19-Oct-2023
 * Time: 22:15
 * Enumeration type: con interfaccia [type]
 */
public enum Utente implements Type {
    gac("gac", "gacgac", RoleEnum.developer, RoleEnum.admin, RoleEnum.user),
    alex("alex", "alexalex", RoleEnum.admin, RoleEnum.user),
    milite("milite", "milite", RoleEnum.user),
    anonimo("anonimo", "anonimo", RoleEnum.anonymous),
    ;

    public String username;

    public String password;

    public List<RoleEnum> userRoles;

    Utente(String username, String password, RoleEnum... ruoli) {
        this.username = username;
        this.password = password;

        if (ruoli != null) {
            this.userRoles = new ArrayList<>();
            for (RoleEnum role : ruoli) {
                userRoles.add(role);
            }

        }
    }

    public static List<Utente> getAllEnums() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<Utente> getAll() {
        return Arrays.stream(values()).toList();
    }

    @Override
    public List<String> getAllTags() {
        List<String> listaTags = new ArrayList<>();

        getAllEnums().forEach(group -> listaTags.add(group.getTag()));
        return listaTags;
    }

    @Override
    public String getTag() {
        return username;
    }

}
