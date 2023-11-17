package entities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {
    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        Rating[] values = Rating.values();
        for (Rating rating : values) {
            if(rating.getValue().equals(dbData)){
                return rating;
            }
        }
        return null;
    }
}
