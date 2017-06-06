module Autocomplete
  class Armarios < ApplicationAutocomplete

    private

    def result_partial(armario)
      ApplicationController.new.render_to_string(partial: 'armarios/autocomplete', locals: { armario: armario }).html_safe
    end

    def result_value(armario)
      armario.ci_armario
    end

    def results
      @results ||= Armario.where('ci_armario LIKE :query', query: "%#{params[:term]}%").limit(5)
    end

  end
end
