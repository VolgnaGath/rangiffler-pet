package org.rangiffler.jupiter.extension;

import org.rangiffler.db.entity.Authority;
import org.rangiffler.db.entity.AuthorityEntity;
import org.rangiffler.db.entity.UserAuthEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.jupiter.annotation.GenerateUserInAuthDb;
import org.rangiffler.model.UserJson;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.extension.*;
import org.rangiffler.db.dao.RangifflerUsersDAO;
import org.rangiffler.db.dao.RangifflerUsersDAOHibernate;

import java.util.Arrays;

public class GenerateUserInAuthDataBaseExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver, SuiteExtension {

    public static ExtensionContext.Namespace GENERATE_USER_DB_NAMESPACE = ExtensionContext.Namespace
            .create(GenerateUserInAuthDataBaseExtension.class);

    private static final RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GenerateUserInAuthDb annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateUserInAuthDb.class);

        if (annotation != null) {
            UserAuthEntity dbUserAuthEntity = new UserAuthEntity();
            dbUserAuthEntity.setUsername(annotation.username());
            dbUserAuthEntity.setPassword(annotation.password());
            dbUserAuthEntity.setEnabled(true);
            dbUserAuthEntity.setAccountNonExpired(true);
            dbUserAuthEntity.setAccountNonLocked(true);
            dbUserAuthEntity.setCredentialsNonExpired(true);
            dbUserAuthEntity.setAuthorities(Arrays.stream(Authority.values()).map(
                    a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(dbUserAuthEntity);
                        return ae;
                    }
            ).toList());
            usersDAO.createUser(dbUserAuthEntity);

            UserJson user = new UserJson();
            user.setUsername(annotation.username());
            user.setPassword(annotation.password());

            context.getStore(GENERATE_USER_DB_NAMESPACE).put("entity", dbUserAuthEntity);
            context.getStore(GENERATE_USER_DB_NAMESPACE).put("user", user);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        UserAuthEntity entity = context.getStore(GENERATE_USER_DB_NAMESPACE).get("entity", UserAuthEntity.class);
        usersDAO.removeUser(entity);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(GENERATE_USER_DB_NAMESPACE).get("user", UserJson.class);
    }

    @Override
    public void afterSuite() {
        EmfProvider.INSTANCE.storedEmf()
                .forEach(EntityManagerFactory::close);
    }
}
