/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marlonprudente.as;

import java.util.HashMap;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class Database {

    public HashMap<String, String> usuarios;

    public Database() {
        usuarios = new HashMap<>();
        usuarios.put("cliente", "4983a0ab83ed86e0e7213c8783940193");
        usuarios.put("tgs", "b8e0e9cce4f6ba9e35f9a40b439530cb");
        usuarios.put("marlon", "c8f759a539858b08e9e46251b1ae9f09");
    }

}
