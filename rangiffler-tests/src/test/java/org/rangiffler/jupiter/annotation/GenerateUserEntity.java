package org.rangiffler.jupiter.annotation;


import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(GenerateUserExtension.class)
public @interface GenerateUserEntity {

    String username() default "";


    String password() default "";

    boolean enabled() default true;


    boolean accountNonExpired() default true;

    boolean accountNonLocked() default true;

    boolean credentialsNonExpired() default true;



}
