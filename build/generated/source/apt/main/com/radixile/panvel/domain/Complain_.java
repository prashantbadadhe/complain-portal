package com.radixile.panvel.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Complain.class)
public abstract class Complain_ {

	public static volatile SingularAttribute<Complain, byte[]> img;
	public static volatile SingularAttribute<Complain, String> imgContentType;
	public static volatile SingularAttribute<Complain, String> expection;
	public static volatile SingularAttribute<Complain, String> details;
	public static volatile SingularAttribute<Complain, Boolean> showAnonymous;
	public static volatile SingularAttribute<Complain, Long> id;
	public static volatile SingularAttribute<Complain, Department> department;
	public static volatile SingularAttribute<Complain, String> resolution;
	public static volatile SingularAttribute<Complain, String> escalate;
	public static volatile SingularAttribute<Complain, Status> status;

}

