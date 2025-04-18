package angelolaera.cashuboli_capstone_backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinaryUploader(
            @Value("${cloudinary.name}") String name,
            @Value("${cloudinary.key}") String key,
            @Value("${cloudinary.secret}") String secret) {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", key);
        config.put("api_secret", secret);

        return new Cloudinary(config);
    }
}