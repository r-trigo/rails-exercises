class Foto < ApplicationRecord
  #attr_accessible :armario_id, :nome, :image
  belongs_to :armario
  mount_uploader :image, ImageUploader
  mount_base64_uploader :image, ImageUploader
end