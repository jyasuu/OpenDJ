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
 * Copyright 2009 Sun Microsystems, Inc.
 * Portions Copyright 2015-2016 ForgeRock AS.
 */
package org.forgerock.opendj.ldap.schema;

import static org.testng.Assert.assertEquals;

import org.forgerock.opendj.ldap.Assertion;
import org.forgerock.opendj.ldap.ByteString;
import org.forgerock.opendj.ldap.ConditionResult;
import org.forgerock.opendj.ldap.DecodeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test The equality matching rules and the equality matching rule api.
 */
@SuppressWarnings("javadoc")
public abstract class MatchingRuleTest extends AbstractSchemaTestCase {
    /**
     * Generate invalid assertion values for the Matching Rule test.
     *
     * @return the data for the EqualityMatchingRulesInvalidValuestest.
     */
    @DataProvider(name = "matchingRuleInvalidAssertionValues")
    public Object[][] createMatchingRuleInvalidAssertionValues() {
        return createMatchingRuleInvalidAttributeValues();
    }

    /**
     * Generate invalid attribute values for the Matching Rule test.
     *
     * @return the data for the EqualityMatchingRulesInvalidValuestest.
     */
    @DataProvider(name = "matchingRuleInvalidAttributeValues")
    public abstract Object[][] createMatchingRuleInvalidAttributeValues();

    /**
     * Generate data for the Matching Rule test.
     *
     * @return the data for the equality matching rule test.
     */
    @DataProvider(name = "matchingrules")
    public abstract Object[][] createMatchingRuleTest();

    /**
     * Test the normalization and the comparison of valid values.
     */
    @Test(dataProvider = "matchingrules")
    public void matchingRules(final String value1, final String value2, final ConditionResult result)
            throws Exception {
        final MatchingRule rule = getRule();

        // normalize the 2 provided values and check that they are equals
        final ByteString normalizedValue1 =
                rule.normalizeAttributeValue(ByteString.valueOfUtf8(value1));
        final Assertion assertion = rule.getAssertion(ByteString.valueOfUtf8(value2));

        final ConditionResult liveResult = assertion.matches(normalizedValue1);
        assertEquals(liveResult, result);
    }

    /**
     * Test that invalid values are rejected.
     */
    @Test(expectedExceptions = DecodeException.class,
            dataProvider = "matchingRuleInvalidAssertionValues")
    public void matchingRulesInvalidAssertionValues(final String value) throws Exception {
        // Get the instance of the rule to be tested.
        final MatchingRule rule = getRule();

        rule.getAssertion(ByteString.valueOfUtf8(value));
    }

    /**
     * Test that invalid values are rejected.
     */
    @Test(expectedExceptions = DecodeException.class,
            dataProvider = "matchingRuleInvalidAttributeValues")
    public void matchingRulesInvalidAttributeValues(final String value) throws Exception {
        // Get the instance of the rule to be tested.
        final MatchingRule rule = getRule();

        rule.normalizeAttributeValue(ByteString.valueOfUtf8(value));
    }

    /**
     * Get an instance of the matching rule.
     *
     * @return An instance of the matching rule to test.
     */
    protected abstract MatchingRule getRule();
}
