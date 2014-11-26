#!/usr/bin/env ruby

require 'bundler'
Bundler.require(:default)

require './mbank/mbank.rb'

include Capybara::DSL
Capybara.default_driver = :selenium

MBank::download_history(ARGV[0], ARGV[1])
