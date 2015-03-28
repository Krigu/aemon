package ch.aemon.rs.testutils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public class RequestBuilder<T> {

    private final Client client = ClientBuilder.newClient();

    private final Class<T> clazz;

    public RequestBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected List<T> getList(String requestUri) {

        Invocation.Builder requestBuilder = client.target(requestUri).request(MediaType.APPLICATION_JSON);

        ParameterizedType parameterizedGenericType = new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{clazz};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        };

        GenericType<List<T>> type = new GenericType<List<T>>(
                parameterizedGenericType) {
        };

        return requestBuilder.get(type);
    }

    protected T post(T item, String requestUri) {
        return client.target(requestUri)
                .request()
                .post(Entity.entity(item, MediaType.APPLICATION_JSON), clazz);
    }


    public T getById(Long id, String requestUri) {
        return client.target(requestUri).path("/{id}")
                .resolveTemplate("id", id).request().get(clazz);
    }

    public Response delete(Long id, String requestUri) {
        return client.target(requestUri).path("/{id}")
                .resolveTemplate("id", id).request().delete();

    }
}
