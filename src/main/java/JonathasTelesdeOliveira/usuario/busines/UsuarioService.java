package JonathasTelesdeOliveira.usuario.busines;


import JonathasTelesdeOliveira.usuario.busines.converter.UsuarioConverter;
import JonathasTelesdeOliveira.usuario.busines.dto.EnderecoDTO;
import JonathasTelesdeOliveira.usuario.busines.dto.TelefoneDTO;
import JonathasTelesdeOliveira.usuario.busines.dto.UsuarioDTO;
import JonathasTelesdeOliveira.usuario.ifraestruture.entity.Endereco;
import JonathasTelesdeOliveira.usuario.ifraestruture.entity.Telefone;
import JonathasTelesdeOliveira.usuario.ifraestruture.entity.Usuario;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.AuthorizationDeniedException;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.ConflictException;
import JonathasTelesdeOliveira.usuario.ifraestruture.exceptions.ResourceNotFoundException;
import JonathasTelesdeOliveira.usuario.ifraestruture.repository.EnderecoRepository;
import JonathasTelesdeOliveira.usuario.ifraestruture.repository.TelefoneRepository;
import JonathasTelesdeOliveira.usuario.ifraestruture.repository.UsuarioRepository;
import JonathasTelesdeOliveira.usuario.ifraestruture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
private final UsuarioRepository usuarioRepository;
private final EnderecoRepository enderecoRepository;
private final TelefoneRepository telefoneRepository;
private final PasswordEncoder passwordEncoder;
private final UsuarioConverter usuarioConverter;
private final AuthenticationManager authenticationManager;
private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }
    public String autenticarUsuario(UsuarioDTO usuarioDTO){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                            usuarioDTO.getSenha())
            );
            return "Bearer " + jwtUtil.generateToken(authentication.getName());
        } catch (BadCredentialsException | UsernameNotFoundException | AuthorizationDeniedException e) {
            throw new UsernameNotFoundException("Usuário ou senha inválidos: ", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }
    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarPorEmail(String email){
        try {
            return  usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(() ->
                            new ResourceNotFoundException("Erro ao Buscar Email" + email)));
        }catch (ResourceNotFoundException e){
                throw new ResourceNotFoundException("Email não encontrado!"+ e);}
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteAllByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto){
        /* buscou o email através do Token */
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        /* Encriptando a senha caso tenha passado novamente */
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Buscar os dados do usuário no banco de dados do UsuarioRepository
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        /* Mesclou os dadso que recebemos com o DTO */
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        /* Salvou dodos do usuário convertido depois converteu os dados do usuario DTO */
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public EnderecoDTO atualizarDadosEndereco(Long id, EnderecoDTO dto){
        Endereco entity = enderecoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("ID não encontrado! "));
        Endereco endereco = usuarioConverter.updadeEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }
    public TelefoneDTO atualizarDadosTelefone(Long id, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Telefone não encontrado! "));
        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO dto) {
        /* buscou o email através do Token */
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email já cadastrado"));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(
                enderecoRepository.save(endereco));
    }
    public TelefoneDTO cadastroTelefone(String token, TelefoneDTO dto){
        /* buscou o email através do Token */
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não encontrado! "));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(
                telefoneRepository.save(telefone)
        );
    }

}
