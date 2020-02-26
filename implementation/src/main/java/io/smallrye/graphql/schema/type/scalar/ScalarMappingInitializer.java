/*
 * Copyright 2020 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.smallrye.graphql.schema.type.scalar;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.jandex.DotName;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;
import io.smallrye.graphql.schema.type.scalar.number.BigDecimalScalar;
import io.smallrye.graphql.schema.type.scalar.number.BigIntegerScalar;
import io.smallrye.graphql.schema.type.scalar.number.FloatScalar;
import io.smallrye.graphql.schema.type.scalar.number.IntegerScalar;
import io.smallrye.graphql.schema.type.scalar.time.DateScalar;
import io.smallrye.graphql.schema.type.scalar.time.DateTimeScalar;
import io.smallrye.graphql.schema.type.scalar.time.TimeScalar;

/**
 * Creating the scalars as needed
 * 
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class ScalarMappingInitializer {

    @Produces
    public Map<DotName, GraphQLScalarType> getScalarMap() {
        return MAPPING;
    }

    private static final Map<DotName, GraphQLScalarType> MAPPING = new HashMap<>();

    static {

        MAPPING.put(DotName.createSimple(char.class.getName()), Scalars.GraphQLString);
        MAPPING.put(DotName.createSimple(Character.class.getName()), Scalars.GraphQLString);

        MAPPING.put(DotName.createSimple(String.class.getName()), Scalars.GraphQLString);
        MAPPING.put(DotName.createSimple(UUID.class.getName()), Scalars.GraphQLString);
        MAPPING.put(DotName.createSimple(URL.class.getName()), Scalars.GraphQLString);
        MAPPING.put(DotName.createSimple(URI.class.getName()), Scalars.GraphQLString);

        MAPPING.put(DotName.createSimple(Boolean.class.getName()), Scalars.GraphQLBoolean);
        MAPPING.put(DotName.createSimple(boolean.class.getName()), Scalars.GraphQLBoolean);

        mapType(new IntegerScalar());
        mapType(new FloatScalar());
        mapType(new BigIntegerScalar());
        mapType(new BigDecimalScalar());
        mapType(new DateScalar());
        mapType(new TimeScalar());
        mapType(new DateTimeScalar());
    }

    private static void mapType(Transformable transformable) {
        for (Class c : transformable.getSupportedClasses()) {
            MAPPING.put(DotName.createSimple(c.getName()), (GraphQLScalarType) transformable);
        }
    }

}