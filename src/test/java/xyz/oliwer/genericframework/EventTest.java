package xyz.oliwer.genericframework;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.oliwer.genericframework.event.ContextInformation;
import xyz.oliwer.genericframework.event.EventContext;
import xyz.oliwer.genericframework.event.EventProvider;
import xyz.oliwer.genericframework.event.EventRegistry;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static xyz.oliwer.genericframework.EventTest.Property.LOADED_STATE;
import static xyz.oliwer.genericframework.EventTest.Property.LOADING_STATE;

@TestInstance(Lifecycle.PER_CLASS)
public final class EventTest {
    @EventProvider(name = "property")
    static final class Property {
        @ContextInformation(provider = Property.class)
        static final class StateContext extends EventContext<Property> {
            StateContext(Property property, byte oldState, byte state) {
                super(property);
                this.oldState = oldState;
                this.state = state;
            }
            final byte oldState;
            final byte state;
        }

        static final byte LOADING_STATE = -0x01;
        static final byte LOADED_STATE  = 0x00;

        byte state;

        Property(byte initialState) {
            this.state = initialState;
        }

        void updateState(byte state) {
            final byte old = this.state;
            this.state = state;

            final EventRegistry registry = EventRegistry.get();
            registry.call(StateContext.class, new StateContext(this, old, state));
        }
    }

    private Property property;

    @BeforeAll void setup() {
        this.property = new Property(LOADING_STATE);
    }

    @Test void handle() {
        final EventRegistry registry = EventRegistry.get();
        assertTrue(registry.attachProvider(Property.class));
        registry.listen(Property.class, Property.StateContext.class, context ->
            System.out.printf(
                "property (%s) state was changed to %s from %s\n",
                context.getSource(), context.state, context.oldState
            )
        );
        property.updateState(LOADED_STATE);
    }
}