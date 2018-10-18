package project.model.domain.converter;

import project.model.domain.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role role) {
        return role.name();
    }

    @Override
    public User.Role convertToEntityAttribute(String s) {
        return User.Role.valueOf(s);
    }
}
