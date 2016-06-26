'use strict'

var browserify = require('browserify')
var gulp = require('gulp')
var sass = require('gulp-sass')
var gutil = require('gulp-util')
var uglify = require('gulp-uglify')
var source = require('vinyl-source-stream')
var buffer = require('vinyl-buffer')
var vueify = require('vueify')

gulp.task('default', ['script', 'style']);

gulp.task('watch', function () {
    gulp.watch('index.js', ['script'])
    gulp.watch('*.vue', ['script'])
    gulp.watch('./sass/**/*.sass', ['style'])
})

gulp.task('script', function () {
    return browserify('./index.js')
            .transform(vueify)
            .bundle()
            .pipe(source('app.js'))
            .pipe(buffer())
            .pipe(gulp.dest('js'));
})

gulp.task('style', function () {
  gulp.src('./sass/**/*.sass')
      .pipe(sass().on('error', sass.logError))
      .pipe(gulp.dest('./css/'))
})
