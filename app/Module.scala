import com.google.inject.AbstractModule
import conf.{AppConfig, AppConfigTest}
import play.api.{Configuration, Environment, Mode}

class Module(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    environment.mode match {
      case Mode.Test => bind(classOf[AppConfig]).to(classOf[AppConfigTest])
      case _ => bind(classOf[AppConfig]).to(classOf[AppConfig])
    }
  }
}