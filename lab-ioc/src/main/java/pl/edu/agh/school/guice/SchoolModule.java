package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import java.util.HashSet;
import java.util.Set;

public class SchoolModule extends AbstractModule {

    @Provides
    public PersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Singleton
    public Logger provideLogger() {
        Set<IMessageSerializer> serializers = new HashSet<>();
        serializers.add(new FileMessageSerializer("persistence.log"));
        return new Logger(serializers);
    }

    @Provides
    @ClassesStorage
    public String provideClassesStoreName() {
        return "classes2.dat";
    }

    @Provides
    @TeachersStorage
    public String provideTeachersStoreName() {
        return "teachers2.dat";
    }
}
