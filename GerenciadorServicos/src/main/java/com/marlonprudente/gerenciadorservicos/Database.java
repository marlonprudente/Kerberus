/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.gerenciadorservicos;

import java.util.HashMap;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class Database {

    public HashMap<String, String> usuarios;

    public Database() {
        usuarios = new HashMap<>();
        usuarios.put("server", "cf1e8c14e54505f60aa10ceb8d5d8ab3");
    }

}
