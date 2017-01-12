class Pkm < ApplicationRecord
  belongs_to :region

  def self.search(region_id)
    if region_id && region_id != ''
      Pkm.where(['region_id = ?', region_id])
    else
      Pkm.all
    end
  end
end