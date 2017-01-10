class Pkm < ApplicationRecord
  belongs_to :region

  def self.search(region_id)
    return all unless region_id.present?
    where(['region_id = ?', region_id])
  end
end
