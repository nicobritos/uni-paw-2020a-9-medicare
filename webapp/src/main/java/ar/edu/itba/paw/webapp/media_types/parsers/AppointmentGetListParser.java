package ar.edu.itba.paw.webapp.media_types.parsers;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.webapp.media_types.AppointmentMIME;
import ar.edu.itba.paw.webapp.media_types.parsers.serializers.AppointmentSerializer;
import ar.edu.itba.paw.webapp.media_types.parsers.utils.GenericParser;
import org.glassfish.jersey.message.internal.ReaderWriter;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

@Produces(AppointmentMIME.GET_LIST)
public class AppointmentGetListParser extends GenericParser<Collection<Appointment>> {
    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Collection<Appointment> appointments, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, ReaderWriter.getCharset(mediaType));
            writer.write(AppointmentSerializer.instance.toJsonArray(appointments).toString());
            writer.flush();
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }
}
