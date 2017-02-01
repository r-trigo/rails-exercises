class ApplicationMailer < ActionMailer::Base
  default from: 'from@example.com'
  layout 'mailer'

  def welcome_email(user)
    @user = user
    @url = 'http://localhost:3000/login'
    mail(to: @user.email, subject: 'Welcome to my awesome rails app')
  end
end
