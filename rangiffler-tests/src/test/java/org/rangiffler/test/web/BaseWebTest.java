package org.rangiffler.test.web;

import org.rangiffler.config.Config;
import org.rangiffler.jupiter.annotation.WebTest;

@WebTest
public abstract class BaseWebTest {

    protected static final Config CFG = Config.getConfig();

}

