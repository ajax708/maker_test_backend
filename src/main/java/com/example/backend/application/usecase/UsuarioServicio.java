package com.example.backend.application.usecase;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.application.port.in.ResultadoAutenticacion;
import com.example.backend.application.port.in.UsuarioCasoUso;
import com.example.backend.application.port.out.GeneradorTokenPuerto;
import com.example.backend.application.port.out.RepositorioUsuarioPuerto;
import com.example.backend.domain.exception.CredencialesInvalidasExcepcion;
import com.example.backend.domain.exception.OperacionInvalidaExcepcion;
import com.example.backend.domain.model.Rol;
import com.example.backend.domain.model.Usuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicio implements UsuarioCasoUso {

    private final RepositorioUsuarioPuerto repositorioUsuario;
    private final PasswordEncoder passwordEncoder;
    private final GeneradorTokenPuerto generadorToken;

    @Override
    public Usuario registrar(String correo, String contrasenaPlana) {
        if (repositorioUsuario.existePorCorreo(correo)) {
            throw new OperacionInvalidaExcepcion("Ya existe un usuario con el correo " + correo);
        }
        Usuario usuario = Usuario.builder()
                .correo(correo)
                .contrasenaHash(passwordEncoder.encode(contrasenaPlana))
                .rol(Rol.USUARIO)
                .build();
        return repositorioUsuario.guardar(usuario);
    }

    @Override
    public ResultadoAutenticacion autenticar(String correo, String contrasena) {
        Usuario usuario = repositorioUsuario.buscarPorCorreo(correo)
                .orElseThrow(CredencialesInvalidasExcepcion::new);

        if (!passwordEncoder.matches(contrasena, usuario.getContrasenaHash())) {
            throw new CredencialesInvalidasExcepcion();
        }

        String token = generadorToken.generarToken(usuario);
        return new ResultadoAutenticacion(token, usuario.getCorreo(), usuario.getRol());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repositorioUsuario.buscarPorId(id);
    }
}
