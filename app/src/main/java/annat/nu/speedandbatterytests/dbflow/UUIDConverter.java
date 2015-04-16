package annat.nu.speedandbatterytests.dbflow;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.util.UUID;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class UUIDConverter extends TypeConverter<String, UUID> {
    @Override
    public String getDBValue(UUID model) {
        return model.toString();
    }

    @Override
    public UUID getModelValue(String data) {
        return UUID.fromString(data);
    }
}
