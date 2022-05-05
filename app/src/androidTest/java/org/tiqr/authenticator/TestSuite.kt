package org.tiqr.authenticator

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(AboutTest::class, AuthenticateTest::class, EnrollTest::class)
class TestSuite