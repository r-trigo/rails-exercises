class Movie < ApplicationRecord

  has_attached_file :poster, styles: { medium: "400x600#" }
  validates_attachment_content_type :poster, content_type: ["image/jpg", "image/jpeg", "image/png", "image/gif"]

end