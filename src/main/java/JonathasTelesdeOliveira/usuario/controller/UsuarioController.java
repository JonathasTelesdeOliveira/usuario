package JonathasTelesdeOliveira.usuario.controller;

import JonathasTelesdeOliveira.usuario.busines.UsuarioService;
import JonathasTelesdeOliveira.usuario.busines.dto.EnderecoDTO;
import JonathasTelesdeOliveira.usuario.busines.dto.TelefoneDTO;
import JonathasTelesdeOliveira.usuario.busines.dto.UsuarioDTO;
import JonathasTelesdeOliveira.usuario.ifraestruture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuario")
@RestController
@RequiredArgsConstructor

public class UsuarioController {

private final UsuarioService usuarioService;
private final AuthenticationManager authenticationManager;
private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvarUsuarioDTO(usuarioDTO));
    }

   /* @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvarUsusario(usuario));
    } */

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email")String email){
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<UsuarioDTO>atualizarUsuario(@RequestBody UsuarioDTO dto,
                                                      @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, dto));
    }
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO>atualizarEnderecoId(@RequestBody EnderecoDTO dto,
                                                          @RequestParam("id")Long id){
        return ResponseEntity.ok(usuarioService.atualizarDadosEndereco(id, dto));
    }
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO>atualizarTelefoneId(@RequestBody TelefoneDTO dto,
                                                          @RequestParam("id")Long id){
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(id, dto));
    }
    @PutMapping("/enderecos")
    public ResponseEntity<EnderecoDTO>cadastraEndereco(@RequestBody EnderecoDTO dto,
                                                          @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.cadastroEndereco(token, dto));
    }
    @PutMapping("/telefones")
    public ResponseEntity<TelefoneDTO>cadastraTelefone(@RequestBody TelefoneDTO dto,
                                                          @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.cadastroTelefone(token, dto));
    }

}
