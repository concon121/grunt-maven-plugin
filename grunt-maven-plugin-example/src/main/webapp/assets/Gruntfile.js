/* jshint node: true */
module.exports = function (grunt) {
    'use strict';

    var path = require('path');
    // Project configuration.
    var gruntConfig = {

        // Metadata.
        pkg: grunt.file.readJSON('package.json'),
        jqueryCheck: 'if (typeof jQuery === "undefined") { throw new Error("Build here requires jQuery") }\n\n',

        concat: {
            options: {
                stripBanners: false
            },
            thirdparty: {
                src: [
                  'javascripts/thirdparty/underscore-min.js',
                  'javascripts/thirdparty/jquery.min.js',
                  'javascripts/thirdparty/jquery-ui.min.js',
                  'javascripts/thirdparty/knockout.min.js',
                  'javascripts/thirdparty/knockout.validation.min.js'
                ],
                dest: 'public/registration.thirdparty.js'
            }

        }, uglify: {
            //see https://github.com/gruntjs/grunt-contrib-uglify#uglify-task for available options
            options: {
                report: 'min', mangle: true, beautify: {
                    beautify: false, indent_level: 2, space_colon: false
                    , ascii_only: true, quote_keys: true
                }, preserveComments: false
            }, 'public/thirdparty.min.js': ['<%= concat.thirdparty.dest %>']
        },

        // cleanup in the public dir
        clean: {
            dist: {
                options: {force: true},
                src: [
                    '!public/**/{.svn}',
                    '.public/**/*.*',
                    '!public/js/*.js',
                    '!public/{.svn}',
                    '!public/css/old-css/*.css',

                    'public/*.{dev,min}.{js}',
                ]
            },
            css: {
                options: {force: true},
                src: [
                    'public/css/**/*.css'
                ]
            }
        },

        compass: {
            dev: {
                options: {
                    basePath: '',
                    config: 'config.rb'
                }
            }
        },

    };//end gruntconfig


    grunt.initConfig(gruntConfig);

    // Loads in plugins from package.json file.
    require('load-grunt-tasks')(grunt);

    //copy files in place in public folder
    grunt.registerTask('dist-static', ['copy']);

    // CSS distribution task.
    grunt.registerTask('dist-css', ['compass']);

    // JS distribution task.
    grunt.registerTask('dist-js', ['concat', 'uglify']);

    // Full distribution task.
    grunt.registerTask('dist', ['clean', 'dist-css', 'dist-js']);

    // Default task.
    grunt.registerTask('default', ['dist', 'json_generator']);

};
