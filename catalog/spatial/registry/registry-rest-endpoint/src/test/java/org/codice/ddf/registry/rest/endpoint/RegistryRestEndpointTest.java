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
package org.codice.ddf.registry.rest.endpoint;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import org.codice.ddf.parser.ParserException;
import org.codice.ddf.registry.federationadmin.service.FederationAdminException;
import org.codice.ddf.registry.federationadmin.service.FederationAdminService;
import org.codice.ddf.registry.rest.endpoint.publication.RegistryPublicationHelper;
import org.codice.ddf.registry.rest.endpoint.report.RegistryReportHelper;
import org.junit.Before;
import org.junit.Test;

import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryPackageType;

public class RegistryRestEndpointTest {

    private FederationAdminService federationAdminService = mock(FederationAdminService.class);

    private RegistryRestEndpoint restEndpoint;

    private RegistryReportHelper reportHelper = mock(RegistryReportHelper.class);

    private RegistryPublicationHelper publicationHelper = mock(RegistryPublicationHelper.class);

    private RegistryPackageType registryPackage = mock(RegistryPackageType.class);

    @Before
    public void setup() {
        restEndpoint = new RegistryRestEndpoint();
        restEndpoint.setFederationAdminService(federationAdminService);
        restEndpoint.setReportHelper(reportHelper);
        restEndpoint.setPublicationHelper(publicationHelper);

        when(reportHelper.buildRegistryMap(any(RegistryPackageType.class))).thenReturn(new HashMap<>());
    }

    @Test
    public void testReportWithBlankMetacard() {
        RegistryRestEndpoint reportViewer = new RegistryRestEndpoint();
        Response response = reportViewer.viewRegInfoHtml(null, null);
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testReportWithFederationAdminException() throws FederationAdminException {
        when(federationAdminService.getRegistryObjectByRegistryId(anyString(),
                anyList())).thenThrow(new FederationAdminException());
        Response response = restEndpoint.viewRegInfoHtml("metacardId", Arrays.asList("sourceIds"));
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }

    @Test
    public void testReportRegistryPackageNotFound() throws FederationAdminException {
        when(federationAdminService.getRegistryObjectByRegistryId(anyString(),
                anyList())).thenReturn(null);
        Response response = restEndpoint.viewRegInfoHtml("metacardId", Arrays.asList("sourceIds"));
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void testReportWithRegistryPackage() throws ParserException, FederationAdminException {
        when(federationAdminService.getRegistryObjectByRegistryId(anyString(),
                anyList())).thenReturn(registryPackage);

        Response response = restEndpoint.viewRegInfoHtml("metacardId", Arrays.asList("sourceIds"));
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.OK.getStatusCode()));

    }

    @Test
    public void testPublishMissingArgs() throws Exception {
        Response response = restEndpoint.publish(null, null);
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testPublishHelperException() throws Exception {
        doThrow(new FederationAdminException("error")).when(publicationHelper)
                .publish("regid", "url source name");

        Response response = restEndpoint.publish("regid", "url+source+name");
        verify(publicationHelper).publish("regid", "url source name");

        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testPublish() throws Exception {
        Response response = restEndpoint.publish("regid", "url+source+name");
        verify(publicationHelper).publish("regid", "url source name");
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void testUnpublishMissingArgs() throws Exception {
        Response response = restEndpoint.unpublish(null, null);
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testUnpublishHelperException() throws Exception {
        doThrow(new FederationAdminException("error")).when(publicationHelper)
                .unpublish(anyString(), anyString());

        Response response = restEndpoint.unpublish("regid", "url+source+name");
        verify(publicationHelper).unpublish("regid", "url source name");

        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void testUnpublish() throws Exception {
        Response response = restEndpoint.unpublish("regid", "url+source+name");
        verify(publicationHelper).unpublish("regid", "url source name");
        assertThat(response.getStatusInfo()
                .getStatusCode(), is(Response.Status.OK.getStatusCode()));
    }
}
