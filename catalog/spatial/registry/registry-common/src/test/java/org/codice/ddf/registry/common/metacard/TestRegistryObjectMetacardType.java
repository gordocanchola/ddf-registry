/**
 * Copyright (c) Codice Foundation
 * <p>
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */

package org.codice.ddf.registry.common.metacard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import ddf.catalog.data.AttributeDescriptor;
import ddf.catalog.data.Metacard;

public class TestRegistryObjectMetacardType {

    @Test
    public void testConstruction() {
        RegistryObjectMetacardType registryObjectMetacardType = new RegistryObjectMetacardType();

        assertThat(registryObjectMetacardType, not(nullValue()));
        Set<AttributeDescriptor> descriptors = registryObjectMetacardType.getAttributeDescriptors();
        assertThat(descriptors, not(nullValue()));
        assertThat(CollectionUtils.isEmpty(descriptors), is(false));

        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.ID)
                .isMultiValued(), is(false));
    }

    @Test
    public void testMetacardHasBasicDescriptorsAsIsStoredTrue() {
        RegistryObjectMetacardType registryObjectMetacardType = new RegistryObjectMetacardType();
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.TAGS)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.ID)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.CONTENT_TYPE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.METADATA)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.CREATED)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.MODIFIED)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.TITLE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.DESCRIPTION)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.SECURITY_LEVEL)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.METACARD_TYPE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.ENTRY_TYPE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.CONTENT_TYPE_VERSION)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.ORGANIZATION_NAME)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.ORGANIZATION_ADDRESS)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.ORGANIZATION_PHONE_NUMBER)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.ORGANIZATION_EMAIL)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.POINT_OF_CONTACT)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.LIVE_DATE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.DATA_START_DATE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.DATA_END_DATE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.LINKS)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(Metacard.GEOGRAPHY)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.REGION)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.DATA_SOURCES)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.DATA_TYPES)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.SERVICE_BINDINGS)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.SERVICE_BINDING_TYPES)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.REGISTRY_ID)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.REGISTRY_IDENTITY_NODE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.REGISTRY_LOCAL_NODE)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.REGISTRY_BASE_URL)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.PUBLISHED_LOCATIONS)
                .isStored(), is(true));
        assertThat(registryObjectMetacardType.getAttributeDescriptor(registryObjectMetacardType.LAST_PUBLISHED)
                .isStored(), is(true));
    }
}
