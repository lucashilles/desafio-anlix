package br.com.lucashilles.domains.common;

import br.com.lucashilles.domains.parameter.ApplicationParameterRepository;
import br.com.lucashilles.services.DataIngestion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommonService {

    @Inject
    DataIngestion dataIngestion;

    @Inject
    ApplicationParameterRepository applicationParameterRepository;

    public void populate() {
        String parameterName = "populated";
        String parameterValue = applicationParameterRepository.getParameter(parameterName);

        if (Boolean.parseBoolean(parameterValue)) {
            throw new RuntimeException("Already populated.");
        }

        dataIngestion.ingestData();

        applicationParameterRepository.upsert(parameterName, Boolean.toString(true));
    }
}
