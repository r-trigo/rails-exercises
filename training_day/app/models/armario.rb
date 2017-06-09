class Armario < ApplicationRecord
  has_many :fotos, as: :imageable
  accepts_nested_attributes_for :fotos, allow_destroy: true, reject_if: :all_blank

end
