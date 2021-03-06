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
        'js/model/Node.js',
        'js/model/FieldDescriptors.js',
        'js/view/Segment.view.js',
        'text!templates/nodeModal.handlebars'
    ],
    function (ich, Marionette, Backbone, wreqr, _, $, Node, FieldDescriptors, Segment, nodeModal) {

        ich.addTemplate('modalNode', nodeModal);

        var NodeModal = {};

        NodeModal.View = Marionette.Layout.extend({
            template: 'modalNode',
            className: 'modal',
            /**
             * Button events, right now there's a submit button
             * I do not know where to go with the cancel button.
             */
            events: {
                "click .submit-button": "submitData",
                "click .cancel-button": "cancel"
            },
            regions: {
                generalInfo: '#generalInfo',
                organizationInfo: '#organizationInfo',
                contactInfo: '#contactInfo',
                serviceInfo: '#serviceInfo',
                contentInfo: '#contentInfo'
            },

            /**
             * Initialize  the binder with the ManagedServiceFactory model.
             * @param options
             */
            initialize: function (options) {
                this.mode = options.mode;
                this.descriptors = FieldDescriptors.retrieveFieldDescriptors();
                this.model.refreshData();
            },
            serializeData: function () {
                var data = {};

                if (this.model) {
                    data = this.model.toJSON();
                }
                data.saveErrors = this.model.saveErrors;
                data.validationErrors = this.model.validationError;
                data.mode = this.mode;

                return data;
            },
            onRender: function () {
                this.$el.attr('role', "dialog");
                this.$el.attr('aria-hidden', "true");

                this.generalInfo.show(new Segment.SegmentCollectionView({
                    model: this.model.generalInfo,
                    collection: this.model.generalInfo.get('segments'),
                    showHeader: true
                }));
                this.organizationInfo.show(new Segment.SegmentCollectionView({
                    model: this.model.organizationInfo,
                    collection: this.model.organizationInfo.get('segments'),
                    showHeader: true
                }));
                this.contactInfo.show(new Segment.SegmentCollectionView({
                    model: this.model.contactInfo,
                    collection: this.model.contactInfo.get('segments'),
                    showHeader: true
                }));
                this.serviceInfo.show(new Segment.SegmentCollectionView({
                    model: this.model.serviceInfo,
                    collection: this.model.serviceInfo.get('segments'),
                    showHeader: true
                }));
                this.contentInfo.show(new Segment.SegmentCollectionView({
                    model: this.model.contentInfo,
                    collection: this.model.contentInfo.get('segments'),
                    showHeader: true
                }));
            },
            /**
             * Submit to the backend.
             */
            submitData: function () {
                wreqr.vent.trigger('beforesave');
                var view = this;
                var response = view.model.save();
                if (response) {
                    response.success(function () {
                        view.closeAndUnbind();
                        if (view.model.addOperation) {
                            wreqr.vent.trigger('nodeAdded');
                        } else {
                            wreqr.vent.trigger('nodeUpdated');
                        }
                    });
                    response.fail(function (val) {
                        view.model.saveErrors = val;
                        view.render();
                    });
                } else {
                    view.render();
                }

            },
            cancel: function () {
                this.model.saveErrors = undefined;
                this.model.validationError = undefined;
                this.closeAndUnbind();
            },
            closeAndUnbind: function () {
                this.$el.modal("hide");
            }
        });

        return NodeModal;

    });