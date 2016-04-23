/* jshint node: true */
module.exports = function (grunt) {
    'use strict';

    var path = require('path');
    // Project configuration.
    var gruntConfig = {

        // Metadata.
        pkg: grunt.file.readJSON('package.json'),
        jqueryCheck: 'if (typeof jQuery === "undefined") { throw new Error("Build here requires jQuery") }\n\n',

        jshint: {
            options: {
                jshintrc: 'javascripts/.jshintrc'
            }, gruntfile: {
                src: 'Gruntfile.js'
            }, src: {
                src: [
                    '!javascripts/thirdparty/*.js',
                    'javascripts/models/*.js',
                    'javascripts/helpers/*.js',
                    'javascripts/*.js'
                ]
            }
        },

        concat: {
            options: {
                stripBanners: false
            },
            app: {
                src: [
                    'javascripts/helpers/*.js',
                    'javascripts/models/*.js'
                ],
                dest: 'public/registration.app.js'
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
            },
            templates: {
              src: [
                'javascripts/views/registrationComplete.html',
                'javascripts/views/registrationForm.html',
                'javascripts/views/partials/button.html',
                'javascripts/views/partials/input.html',
                'javascripts/views/partials/radio.html',

              ],
              dest: 'public/templates.html'
            }

        }, uglify: {
            //see https://github.com/gruntjs/grunt-contrib-uglify#uglify-task for available options
            options: {
                report: 'min', mangle: true, beautify: {
                    beautify: false, indent_level: 2, space_colon: false
                    , ascii_only: true, quote_keys: true
                }, preserveComments: false
            }, 'public/registration.min.js': ['<%= concat.thirdparty.dest %>', '<%= concat.app.dest %>']
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

        json_generator: {
            target: {
                dest: "version.json",
                options: {
                    version: "1.0.0"
                }
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
    grunt.registerTask('default', ['jshint', 'dist', 'json_generator']);

};
