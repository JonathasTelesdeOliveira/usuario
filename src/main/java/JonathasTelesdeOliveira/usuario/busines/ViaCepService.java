package JonathasTelesdeOliveira.usuario.busines;

import JonathasTelesdeOliveira.usuario.ifraestruture.client.ViaCepDTO;
import JonathasTelesdeOliveira.usuario.ifraestruture.client.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    public final ViaCepClient client;

    public ViaCepDTO buscarDadosEndereco(String cep) {
       try {
           return client.buscarDadosEndereco(ValidarCep(cep));
       } catch (IllegalArgumentException e) {
           throw new IllegalArgumentException("Erro ao buscar dados Endereco" + e);
       }
    }

    private String ValidarCep(String cep) {
        String cepFormatado = cep.replace(" ", "")
                .replace("-", "");

        if (!cepFormatado.matches("\\d+")
                || !Objects.equals(cepFormatado.length(), 8)) {
            throw new IllegalArgumentException("O cep contém caracteres inválidos, favor verificar");
        }

        return cepFormatado;
    }

}
