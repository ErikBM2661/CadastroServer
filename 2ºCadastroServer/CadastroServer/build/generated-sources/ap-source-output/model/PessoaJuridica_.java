package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pessoa;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-10T19:01:23", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(PessoaJuridica.class)
public class PessoaJuridica_ { 

    public static volatile SingularAttribute<PessoaJuridica, Pessoa> pessoa;
    public static volatile SingularAttribute<PessoaJuridica, String> cnpj;
    public static volatile SingularAttribute<PessoaJuridica, Integer> iDPessoa;

}