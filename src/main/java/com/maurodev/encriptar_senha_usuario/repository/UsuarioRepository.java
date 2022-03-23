package com.maurodev.encriptar_senha_usuario.repository;

import com.maurodev.encriptar_senha_usuario.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    public Optional<UsuarioModel> findByLogin(String login); // Fazer consulta para retornar o login ou usuario vendo do banco de dados


}
