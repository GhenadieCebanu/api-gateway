package api.gateway.filter;

import java.net.URI;
import java.util.function.Function;

public interface BodyToUriFunction extends Function<String, URI> {

}
