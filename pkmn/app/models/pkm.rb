class Pkm < ApplicationRecord
  belongs_to :region

  def self.search(search)
    if search
      Pkm.where('comp_id LIKE ?', "%#{search}%")
    else
      Pkm.all
    end

    # if region_id && region_id != ''
    #   Pkm.where(['region_id = ?', region_id])
    # else
    #   Pkm.all
    # end
  end

end