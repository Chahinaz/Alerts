package conf

import javax.inject.{Inject, Singleton}

import play.api.Configuration

@Singleton
class AppConfig @Inject() (configuration: Configuration) {
  val dbUrl: String = configuration.getString("database.url").get
  val dbUsername: String = configuration.getString("database.username").get
  val dbPassword: String = configuration.getString("database.password").get
}

class AppConfigTest @Inject() (configuration: Configuration) extends AppConfig(configuration) {
  override val dbUrl: String = configuration.getString("test.database.url").get
  override val dbUsername: String = configuration.getString("test.database.username").get
  override val dbPassword: String = configuration.getString("test.database.password").get
}