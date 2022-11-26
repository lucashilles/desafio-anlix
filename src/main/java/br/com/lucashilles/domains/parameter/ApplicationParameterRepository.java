package br.com.lucashilles.domains.parameter;

import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ApplicationParameterRepository {

    final String profile = ProfileManager.getActiveProfile();

    public String getParameter(String name) {
        String query = "name = ?1 AND profile = ?2";
        ApplicationParameter parameter = ApplicationParameter.find(query, name, profile).firstResult();
        if (parameter == null) {
            return "";
        }
        return parameter.value;
    }

    @Transactional
    public ApplicationParameter upsert(String name, String value) {
        String query = "name = ?1 AND profile = ?2";
        ApplicationParameter applicationParameter = ApplicationParameter.find(query, name, profile).firstResult();

        if (applicationParameter != null) {
            applicationParameter.value = value;
            applicationParameter.persist();

            return applicationParameter;
        }

        applicationParameter = new ApplicationParameter();
        applicationParameter.name = name;
        applicationParameter.profile = profile;
        applicationParameter.value = value;
        applicationParameter.persist();

        return applicationParameter;
    }
}
