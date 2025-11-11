package oogasalad.model;

import java.util.Map;

public record GameObjectPropertiesValuesToTestWith(Map<String, String> interactTransformations,
                                                   String expirable, String image,
                                                   String type, String expireTime,
                                                   String updatable, String updateTime,
                                                   String updateTransformation) {

}
