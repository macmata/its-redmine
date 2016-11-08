gerrit_plugin(
  name = 'its-redmine',
  srcs = glob(['src/main/java/**/*.java']),
  resources = glob(['src/main/resources/**/*']),
  manifest_entries = [
    'Gerrit-PluginName: its-redmine',
    'Gerrit-ReloadMode: reload',
    'Gerrit-ApiVersion: 2.13',
    'Gerrit-ApiType: plugin',
    'Gerrit-Module: com.googlesource.gerrit.plugins.its.redmine.RedmineModule',
    'Implementation-Title: Redmine ITS Plugin',
    'Implementation-URL: http://www.savoirfairelinux.com',
    'Implementation-Vendor: Savoir-faire Linux Inc',
  ],
  deps = [
    '//plugins/its-base:its-base__plugin',
    '//plugins/its-redmine/lib/gerrit:redmine-api',
    '//plugins/its-redmine/lib/gerrit:json-api',
  ],
)



