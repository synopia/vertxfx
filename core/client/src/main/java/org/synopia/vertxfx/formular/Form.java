package org.synopia.vertxfx.formular;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import jidefx.scene.control.validation.ValidationEvent;
import jidefx.scene.control.validation.ValidationMode;
import jidefx.scene.control.validation.ValidationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by synopia on 21.12.2014.
 */
public class Form {
    interface Processor {
        void init(Node node);

        void afterInit(Node node);
    }

    static Map<Class<? extends Annotation>, Processor> processors = Maps.newHashMap();

    static {
        processors.put(Required.class, new Processor() {
            @Override
            public void init(Node node) {
                ValidationUtils.install(node, validationObject -> {
                    Object newValue = validationObject.getNewValue();
                    if (newValue == null || newValue.toString().length() == 0) {
                        return new ValidationEvent(ValidationEvent.VALIDATION_ERROR);
                    } else {
                        return ValidationEvent.OK;
                    }
                }, ValidationMode.ON_FLY);
            }

            @Override
            public void afterInit(Node node) {
                ValidationUtils.forceValidate(node, ValidationMode.ON_FLY);
            }
        });
    }

    class FormField {
        Processor processor;
        Node node;
        boolean valid;

        public FormField(Node node, Processor processor) {
            this.node = node;
            this.processor = processor;
            valid = false;
        }

        void init() {
            processor.init(node);
        }

        void afterInit() {
            processor.afterInit(node);
        }

        void setValid(boolean valid) {
            this.valid = valid;
        }
    }

    private final List<FormField> fields = Lists.newArrayList();
    private final Object presenter;
    private Node root;
    private final Map<Node, FormField> nodes = Maps.newHashMap();
    private SimpleBooleanProperty valid = new SimpleBooleanProperty();

    public Form(Object presenter) {
        this.presenter = presenter;
        Class<?> type = presenter.getClass();
        List<Node> parents = Lists.newArrayList();
        for (Field field : type.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                Processor processor = processors.get(annotation.annotationType());
                if (processor != null) {
                    try {
                        boolean accessible = field.isAccessible();
                        if (!accessible) {
                            field.setAccessible(true);
                        }
                        Node node = (Node) field.get(presenter);
                        field.setAccessible(accessible);
                        parents = findAncestors(parents, getParents(node));
                        FormField formField = new FormField(node, processor);
                        fields.add(formField);
                        nodes.put(node, formField);
                    } catch (IllegalAccessException | ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (fields.size() == 0) {
            throw new IllegalStateException("No fields found");
        }
        if (parents.size() == 0) {
            throw new IllegalStateException("No root node found");
        }

        root = parents.get(parents.size() - 1);
        root.addEventHandler(ValidationEvent.ANY, new EventHandler<ValidationEvent>() {
            @Override
            public void handle(ValidationEvent validationEvent) {
                FormField field = nodes.get(validationEvent.getTarget());
                field.setValid(validationEvent.getEventType() == ValidationEvent.VALIDATION_OK);

                boolean formValid = true;
                for (FormField formField : fields) {
                    if (!formField.valid) {
                        formValid = false;
                        break;
                    }
                }
                valid.set(formValid);
            }
        });
    }

    public Node getRoot() {
        return root;
    }

    public void initialize() {
        fields.forEach(Form.FormField::init);
        fields.forEach(Form.FormField::afterInit);
    }

    private List<Node> findAncestors(List<Node> parents, List<Node> nodeParents) {
        if (parents.size() == 0) {
            parents.addAll(nodeParents);
        } else {
            int size = Math.min(parents.size(), nodeParents.size());
            for (int i = 0; i < size; i++) {
                if (parents.get(i) != nodeParents.get(i)) {
                    parents = parents.subList(0, i);
                    break;
                }
            }
        }
        return parents;
    }

    public boolean getValid() {
        return valid.get();
    }

    public SimpleBooleanProperty validProperty() {
        return valid;
    }

    private List<Node> getParents(Node node) {
        List<Node> parents = Lists.newArrayList();
        Node current = node;
        while (current != null) {
            parents.add(current);
            current = current.getParent();
        }
        Collections.reverse(parents);
        return parents;
    }
}
