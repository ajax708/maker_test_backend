package com.example.backend.infrastructure.persistence.entity;

import com.example.backend.domain.model.Rol;
import com.example.backend.domain.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public static UsuarioEntidad desdeDominio(Usuario usuario) {
        UsuarioEntidad entidad = new UsuarioEntidad();
        entidad.setId(usuario.getId());
        entidad.setCorreo(usuario.getCorreo());
        entidad.setContrasenaHash(usuario.getContrasenaHash());
        entidad.setRol(usuario.getRol());
        return entidad;
    }

    public Usuario aDominio() {
        return Usuario.builder()
                .id(id)
                .correo(correo)
                .contrasenaHash(contrasenaHash)
                .rol(rol)
                .build();
    }
}
