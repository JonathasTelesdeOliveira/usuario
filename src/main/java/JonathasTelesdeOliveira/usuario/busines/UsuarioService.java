package JonathasTelesdeOliveira.usuario.busines;


import JonathasTelesdeOliveira.usuario.busines.converter.UsuarioConverter;
import JonathasTelesdeOliveira.usuario.busines.dto.UsuarioDTO;
import JonathasTelesdeOliveira.usuario.ifraestruture.entity.Usuario;
import JonathasTelesdeOliveira.usuario.ifraestruture.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvarUsuarioDTO(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }




}
