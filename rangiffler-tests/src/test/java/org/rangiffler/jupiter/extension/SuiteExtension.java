package org.rangiffler.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface SuiteExtension extends BeforeAllCallback {

    default void beforeSuite() {
    }

    default void afterSuite() {
    }

    @Override
    default void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(
                        SuiteExtension.class,
                        k -> {
                            beforeSuite();
                            return (ExtensionContext.Store.CloseableResource) this::afterSuite;
                        });
    }
}