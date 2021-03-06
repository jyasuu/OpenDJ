/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2009-2010 Sun Microsystems, Inc.
 * Portions copyright 2012-2013 ForgeRock AS.
 */

package org.forgerock.opendj.ldap.requests;

import java.util.List;

import org.forgerock.opendj.ldap.DecodeException;
import org.forgerock.opendj.ldap.DecodeOptions;
import org.forgerock.opendj.ldap.controls.Control;
import org.forgerock.opendj.ldap.controls.ControlDecoder;

/**
 * The base class of all Requests provides methods for querying and manipulating
 * the set of Controls included with a Request.
 */
public interface Request {

    /**
     * Adds the provided control to this request.
     *
     * @param control
     *            The control to be added to this request.
     * @return This request.
     * @throws UnsupportedOperationException
     *             If this request does not permit controls to be added.
     * @throws NullPointerException
     *             If {@code control} was {@code null}.
     */
    Request addControl(Control control);

    /**
     * Returns {@code true} if this request contains the specified request
     * control.
     *
     * @param oid
     *            The numeric OID of the request control.
     * @return {@code true} if this request contains the specified request
     *         control.
     */
    boolean containsControl(String oid);

    /**
     * Decodes and returns the first control in this request having an OID
     * corresponding to the provided control decoder.
     *
     * @param <C>
     *            The type of control to be decoded and returned.
     * @param decoder
     *            The control decoder.
     * @param options
     *            The set of decode options which should be used when decoding
     *            the control.
     * @return The decoded control, or {@code null} if the control is not
     *         included with this request.
     * @throws DecodeException
     *             If the control could not be decoded because it was malformed
     *             in some way (e.g. the control value was missing, or its
     *             content could not be decoded).
     * @throws NullPointerException
     *             If {@code decoder} or {@code options} was {@code null}.
     */
    <C extends Control> C getControl(ControlDecoder<C> decoder, DecodeOptions options)
            throws DecodeException;

    /**
     * Returns a {@code List} containing the controls included with this
     * request. The returned {@code List} may be modified if permitted by this
     * request.
     *
     * @return A {@code List} containing the controls.
     */
    List<Control> getControls();

}
