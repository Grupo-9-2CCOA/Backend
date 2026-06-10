package school.sptech.projeto_extensao.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;
import school.sptech.projeto_extensao.dto.EventoCalendarDto;

import java.io.InputStream;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
public class GoogleCalendarService {
    private static final String ID_DA_AGENDA = "3db8bb13db53a3d6b5ce7c6c43795907d1d478f0ff226f066d11acc5c56b06f8@group.calendar.google.com";
    private static final String ARQUIVO_CHAVE = "/google-calendar-key.json";

    public String criarEventoNaAgenda(EventoCalendarDto dto) throws Exception {

        InputStream keyStream = getClass().getResourceAsStream(ARQUIVO_CHAVE);
        if (keyStream == null) {
            throw new IllegalStateException("Arquivo de credenciais do Google não encontrado nos resources!");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(keyStream)
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

        Calendar service = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("ProjetoExtensao")
                .build();

        Event event = new Event()
                .setSummary(dto.getTitulo())
                .setDescription(dto.getDescricao());

        ZoneId fusoHorario = ZoneId.systemDefault();

        Date inicioConvertido = Date.from(dto.getDataInicio().atZone(fusoHorario).toInstant());
        EventDateTime start = new EventDateTime()
                .setDateTime(new DateTime(inicioConvertido))
                .setTimeZone(fusoHorario.getId());
        event.setStart(start);

        Date fimConvertido = Date.from(dto.getDataFim().atZone(fusoHorario).toInstant());
        EventDateTime end = new EventDateTime()
                .setDateTime(new DateTime(fimConvertido))
                .setTimeZone(fusoHorario.getId());
        event.setEnd(end);

        Event eventoCriado = service.events().insert(ID_DA_AGENDA, event).execute();

        return eventoCriado.getHtmlLink();
    }
}
