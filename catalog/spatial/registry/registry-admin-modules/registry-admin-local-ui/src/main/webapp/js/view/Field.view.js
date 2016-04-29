/**
 * Copyright (c) Codice Foundation
 *
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 *
 **/
/*global define*/
define([
        'icanhaz',
        'marionette',
        'backbone',
        'wreqr',
        'underscore',
        'jquery',
        'text!templates/field.handlebars'
    ],
    function (ich, Marionette, Backbone, wreqr, _, $,  field) {


        ich.addTemplate('field', field);

        var Field = {};

        Field.FieldView = Marionette.ItemView.extend({
            template: 'field',
            events: {
                "click .remove-field": 'removeField',
                "click .add-value": 'addValue',
                "click .remove-value": 'removeValue'
            },
            initialize: function () {
                this.modelBinder = new Backbone.ModelBinder();
                this.listenTo(wreqr.vent, 'fieldErrorChange:' + this.model.get('key'), this.showHideError);
            },
            onRender: function () {
                var bindings = Backbone.ModelBinder.createDefaultBindings(this.el, 'name');
                this.modelBinder.bind(this.model, this.$el, bindings);
            },
            addValue: function () {
                this.model.addValue('');
                this.render();
            },
            removeValue: function (event) {
                this.model.removeValue(event.target.getAttribute('name'));
                this.render();
            },
            removeField: function () {
                wreqr.vent.trigger('removeField:' + this.options.parentId, this.model.get('key'));
            },
            showHideError: function () {
                this.render();
            },
            serializeData: function () {
                var data = {};

                if (this.model) {
                    data = this.model.toJSON();
                    data.validationError = this.model.hadError;
                    data.errorIndices = this.model.errorIndices;
                }
                return data;
            }
        });

        Field.FieldCollectionView = Marionette.CollectionView.extend({
            itemView: Field.FieldView,
            tagName: 'tr',
            initialize: function (options) {
                this.listenTo(wreqr.vent, 'addedField:' + options.parentId, this.addedField);
                this.listenTo(wreqr.vent, 'removedField:' + options.parentId, this.removedField);

            },
            buildItemView: function (item, ItemViewType, itemViewOptions) {
                var options = _.extend({
                    model: item,
                    parentId: this.options.parentId
                }, itemViewOptions);
                return new ItemViewType(options);
            },
            addedField: function (field) {
                this.collection.add(field);
                this.render();
            },
            removedField: function (field) {
                this.collection.remove(field);
                this.render();
            }
        });

        return Field;

    });