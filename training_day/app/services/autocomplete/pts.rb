module Autocomplete
  class Pts < ApplicationAutocomplete

    private

    def result_partial(pt)
      ApplicationController.new.render_to_string(partial: 'pts/autocomplete', locals: { pt: pt }).html_safe
    end

    def result_value(pt)
      pt.ci_pt
    end

    def results
      @results ||= Pt.where('ci_pt LIKE :query', query: "%#{params[:term]}%").limit(5)
    end

  end
end
