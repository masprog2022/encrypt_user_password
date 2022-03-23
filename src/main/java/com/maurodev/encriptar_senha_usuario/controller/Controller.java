package com.maurodev.encriptar_senha_usuario.controller;

import com.maurodev.encriptar_senha_usuario.model.UsuarioModel;
import com.maurodev.encriptar_senha_usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuario")
public class Controller {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public Controller(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos(){

        return ResponseEntity.ok(repository.findAll());

    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario){
        usuario.setSenha(encoder.encode(usuario.getSenha()));
      return ResponseEntity.ok(repository.save(usuario));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                     @RequestParam String senha){


        Optional<UsuarioModel> optUsuario = repository.findByLogin(login); // consultar o usuario se nao encontrar
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false); // manda um unauthorized como false
        }

        UsuarioModel usuario = optUsuario.get(); //pegar o usuario do optional (aqui pode pegar 1 ou o outro)
        boolean valid = encoder.matches(senha, usuario.getSenha()); // validar se for valida a senha

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED; // se a senha bater status ok se nao unauthorized salva na variavel status
        return ResponseEntity.status(status).body(valid);  // retorna o valor que vai conter em funcao da validacao vai anteriormente

    }

}
